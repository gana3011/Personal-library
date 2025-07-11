import { gql } from "@apollo/client";

export const FETCH_BOOKS = gql`
query FetchBooks($userId: ID!){
fetchBooksByUserId(userId:$userId){
id,
book{
name
}
author{
name
}
status,
dateAdded,
}
}
`