RETROSPECTIVE Team 08
=====================================

### Index

- [process measures](#process-measures)
- [quality measures](#quality-measures)
- [general assessment](#assessment)

## PROCESS MEASURES

### Macro statistics

- 4 stories committed and done
    - Stories 1, 2, 3, 6: Code reviewed and present on VCS
    - Stories 1, 3, 6: Unit tests developed and all passing
- 26 points committed and done
- 115h30m estimated and 107h35m spent

**Remember** a story is done ONLY if it fits the Definition of Done:

- Unit Tests passing
- Code review completed
- Code present on VCS
- End-to-End tests performed

> Please refine your DoD if required (you cannot remove items!)

### Detailed statistics

| Story | # Tasks | Points | Hours est. | Hours actual |
|-------|---------|--------|------------|--------------|
| _#0_  | 18      | -      | 1w2d5h     | 1w1d7h30     |
| _#1_  | 9       | 8      | 3d30m      | 3d1h50m      |
| _#2_  | 8       | 13     | 2d2h30m    | 2d3h30m      |
| _#3_  | 5       | 2      | 1d2h30m    | 5h45m        |
| _#6_  | 1       | 3      | 1h         | 1h           |

> story `#0` is for technical tasks, leave out story points (not applicable in this case)

- Hours per task average, standard deviation (estimate and actual)  
  169hrs per task estimated vs 157hrs per task actual
  standard deviation = 8,19
- Total estimation error ratio: sum of total hours spent / sum of total hours effort - 1

  $$\frac{\sum_i spent_{task_i}}{\sum_i estimation_{task_i}} - 1 = -0,07$$

- Absolute relative task estimation error: sum( abs( spent-task-i / estimation-task-i - 1))/n

  $$\frac{1}{n}\sum_i^n \left| \frac{spent_{task_i}}{estimation_{task_i}}-1 \right| = 0,08$$

## QUALITY MEASURES

- Unit Testing:
    - Total hours estimated
    - Total hours spent
    - Number of automated unit test cases
    - Coverage (if available)
- E2E testing:
    - Total hours estimated
    - Total hours spent
- Code review
    - Total hours estimated
    - Total hours spent

> We considered the testing estimation in the general estimation of every task. So we do not have precise statistics.

## ASSESSMENT

- What caused your errors in estimation (if any)?
    - We didn't know each other, we didn't know the velocity of the team.
    - The initial size of the backend team was too small, so it took them more hours than expected to complete the tasks
      they were assigned to.

- What lessons did you learn (both positive and negative) in this sprint?
    - We need more planning and to pay more attention to documentation.
    - Transparency and communication are key values

- Which improvement goals set in the previous retrospective were you able to achieve?
    - Not applicable

- Which ones you were not able to achieve? Why?
    - Not applicable

- Improvement goals for the next sprint and how to achieve them (technical tasks, team coordination, etc.)
    - Plan tests in advance and write them
    - Write a SSOT (Single Source Of Truth) containing all the necessary information to follow by the team (i.e.
      exchanged payloads between Backend and Frontend, APIs, ...)
    - Detailing the tasks in YouTrack so that everybody knows what they are about (e.g. add tags that help clarify
      what has to be done, ...)
    - Comment the code you are writing!

- One thing you are proud of as a Team!!
    - Communication and working environment