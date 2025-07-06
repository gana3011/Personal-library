import { gql } from "@apollo/client";

export const ADD_USER  = gql`
mutation AddUser($userInput: UserInput!){
addUser(input: $userInput)
}
`;