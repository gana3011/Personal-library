import { gql } from "@apollo/client";

export const ADD_USER  = gql`
mutation AddUser($userInput: UserInput!){
addUser(input: $userInput)
}
`;

export const LOGIN_USER =   gql`
mutation LoginUser($userInput: UserInput!){
loginUser(input: $userInput){
id,
name,
access,
}
}
`