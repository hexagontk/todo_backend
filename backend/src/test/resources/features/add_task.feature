
@Task
Feature:
  Adding a task records it in the database

  Scenario Outline: Change team users
    Given an accepted lead
     When a <userType> is <userAction> to a team
     Then notifies partners via webhook
      And a lead event is recorded
      And lead's quota is updated
      And partner licenses are <licensesAction>

    Examples:
      | userType | userAction | licensesAction |
      | member   | added      | decreased      |
