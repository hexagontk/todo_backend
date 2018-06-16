
@Backend
Feature: Adding a task using the API records it in the database

  Background:
    Given an instance of

  Scenario Outline: Add valid task
    Given a valid task
     When a POST request to the API is made
     Then the task can be fetched back from the API

    Examples:
      | userType | userAction | licensesAction |
      | member   | added      | decreased      |
