import { ApolloClient, from, HttpLink, InMemoryCache } from "@apollo/client";
import { authLink } from "./authLink";

const httpLink = new HttpLink({
  uri: 'http://localhost:8080/graphql',
  credentials: 'include', 
});

const client = new ApolloClient({
  link: from([authLink, httpLink]), 
  cache: new InMemoryCache(),
});


export default client;