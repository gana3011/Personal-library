import { ApolloClient, HttpLink, InMemoryCache } from "@apollo/client";

export const refreshClient = new ApolloClient({
  link: new HttpLink({
    uri: "http://localhost:8080/graphql",
    credentials: "include", 
  }),
  cache: new InMemoryCache(),
});