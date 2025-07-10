import { setContext } from "@apollo/client/link/context";
import { ApolloClient, HttpLink, InMemoryCache, gql } from "@apollo/client";

const REFRESH = gql`
  mutation RefreshToken {
    refresh
  }
`;

const refreshClient = new ApolloClient({
  link: new HttpLink({
    uri: "http://localhost:8080/graphql",
    credentials: "include", 
  }),
  cache: new InMemoryCache(),
});

const isTokenExpired = (token: string): boolean => {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.exp * 1000 < Date.now();
  } catch {
    return true;
  }
};

export const authLink = setContext(async (_, prevContext) => {
  
  if (prevContext?.skipAuth) return {};

  let token = localStorage.getItem('accessToken');

  if (token && isTokenExpired(token)) {
    try {
      const { data } = await refreshClient.mutate({ mutation: REFRESH });
      token = data?.refresh;
      if (token) {
        localStorage.setItem('accessToken', token);
      }
    } catch (err) {
      console.error('Failed to refresh token:', err);
      token = null;
      localStorage.removeItem('accessToken');
    }
  }

  return {
    headers: {
      ...prevContext.headers,
      Authorization: token ? `Bearer ${token}` : '',
    },
  };
});