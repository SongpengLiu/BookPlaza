# Team: CodeMages

## App Name:
BookPlaza


## Team Meeting 2 - Week 7 - 2023-09-16  (2:00 pm-4:00 pm)
**Participants:**
- Songpeng Liu
- Xiangji Kong
- Yucheng Zhu
- Shao An Tay


**scribe:** Songpeng Liu

**editor:** Yucheng Zhu

## Agreed Procedure
Stand up Procedure:
- [Each team member briefly shares their progress and any roadblocks.]
- **Everyone:**
We have read the architecture of the App, and understand assigned part.

- **Songpeng Liu:**
- *Progress:*
- - Designed the architecture of the APP
- - Designed state design pattern in user operation
- - Designed factory design pattern in  file operation
- - Designed singleton design pattern in  file operation

- **Xiangji Kong:**
- *Progress:*
- - Implemented GUI framwork
- - Implemented activity_search_result layout
- - Implemented activity_item_detail layout
- - Implemented activity_main layout

- *Road block:*
- - Precise functions and layout require knowing the data


- **Yucheng Zhu:**
- *Progress:*
- - designed data source structure
- - Scraped 3000 books' data sources
- - implemented AVL tree structure and skeleton
- - implemented B-tree structure and skeleton

- *Road block:*
- - legal issue and difficulty to scrape some websites (e.g. Amazon)
- - Gathered basic data from GoodReads and its links, but further scraping is needed to gather more data
- - Basic data cleansing is needed

- **Shao An Tay:**
- *Progress:*
- - reserached the search operatation
- - researched the tokenlization
- - researched the parse


## Agenda Items
| Number |                                  Item |
|:-------|--------------------------------------:|
| 1      |                            user login |
| 2      |                 basic search function |
| 3      |                        file operation |
| 4      |                        tree operation |
| 5      |                  user state operation |


## Meeting Minutes
- [Summary of the progress reports, discussions, decisions, and matters to be confirmed from the meeting.]

- tree structure
- data store
- book information operation


- **Discussion:**
- *1. tree structure*:
- - we will use AVL tree and B tree to conduct the sorting
- - we will use AVL tree to sort small data in the memory
- - we will use B-tree to sort large data in the disk

- *2. data store*:
- - we will use local binary file to store books information. 
- - Any tree node can be access in the disk (i.e. SSD and HDD)
- - Find a tree node at the 1st byte in the file and the 10000000th byte has the same O(1) speed (but tree look-up is O(log n))
- - we will use local json file to store users information

- *3. book information operation*:
- - User accounts now have two types: common user and administrator
- - Administrator can operate the book information and do search, while common users can only do search.
- - Use the State design pattern to switch between different accounts

- *4. GUI*:
- - A functional UI
- - Precise functions and layout require knowing the data 
- - Collaboration with the data structure writer is needed

- *5. search and grammar*
- - Newly taught topic. Need further research on this topic


## Work Allocations
- Shao An Tay: Implement basic search function
- Songpeng Liu: Implement relevant data operation classes and user state operation
- Xiangji Kong: Implement user layout and user login function
- Yucheng Zhu: Implement relevant tree structure operation and collect more book information

## Action Items
|                Task                 |          Assigned To           |        Due Date         |
|:-----------------------------------:|:------------------------------:|:-----------------------:|
|            Login (Basic)            |          Xiangji Kong          |         Week 8          |
|                 GUI                 |          Xiangji Kong          |         Week 8          |
|        UI-Layout (Customer)         |          Xiangji Kong          | Decided before Sprint 2 |
|        LoadShowData (Basic)         |          Songpeng Liu          |         Week 8          |
|          DataFiles (Basic)          |          Songpeng Liu          |         Week 8          |
|       Data Profile (Customer)       |          Songpeng Liu          | Decided before Sprint 2 |
|      Search-Filter (Customer)       |    Decided before Sprint 2     | Decided before Sprint 2 |
|          Scrape more data           |          Yucheng Zhu           |         Week 8          |
|           Tree (AVL tree)           |          Yucheng Zhu           |         Week 7          |
|            Tree (B-tree)            |          Yucheng Zhu           | Decided before Sprint 2 |
| Data-Deletion (AVL tree) (Customer) |          Yucheng Zhu           |         Week 8          |
|  Data-Deletion (B-tree) (Customer)  |          Yucheng Zhu           | Decided before Sprint 2 |
|       Skeleton and docstring        |          Shao An Tay           |         Week 7          |
|           Search (Basic)            |          Shao An Tay           | Decided before Sprint 2 |
|               Grammar               |          Shao An Tay           |         Week 8          |
|          Integration tests          |          Shao An Tay           | Decided before Sprint 2 |


## Owner (Check that our code meets the criteria)
- Report: Yucheng Zhu
- Teamwork: Yucheng Zhu
- Data Structures: Songpeng Liu
- Code Quality and Organization: Liu
- User Interface / User Experience (UI/UX): Xiangji Kong
- Testing: Shao An Tay
- Creativity: Shao An Tay

## App Idea:
The app now have a clear architecture and implement steps
we analyzed different parts of the architecture and allocate each to relevant owners


## Conflict Resolution Protocol
1. Active Listening:
   - Encourage active listening when conflicts arise.
   - Team members should listen to each other without interrupting and ask clarifying questions to fully understand the issue.

2. Stay Calm and Respectful:
   - Emphasize the importance of maintaining a calm and respectful tone during conflict discussions.
   - Avoid blaming or using accusatory language.

3. Identify the Root Cause:
   - Work together to identify the underlying issues causing the conflict.
   - Often, conflicts arise from miscommunication, misunderstandings, or differing perspectives.

4. Brainstorm Solutions:
   - Encourage the team to brainstorm potential solutions to the conflict.
   - Consider the pros and cons of each solution.

5. Select a Solution:
   - Once multiple solutions have been proposed, select the one that best addresses the root cause and aligns with the team's goals.

6. Document Agreements:
   - Write down the agreed-upon solution and any action steps.
   - Ensure that everyone is clear on their responsibilities and deadlines.

7. Follow Up:
   - Set a follow-up date to review the progress made in implementing the agreed-upon solution.
   - Discuss any adjustments or additional steps needed if the conflict persists.

8. Learn and Grow:
   - Encourage the team to view conflicts as opportunities for growth and learning.
   - Discuss what can be done differently in the future to prevent similar conflicts.

9. Continuous Improvement:
   - Regularly review and refine the conflict resolution process based on team feedback and evolving needs.


## Scribe Rotation
The following dictates who will scribe in this and the next meeting.

|        Name         | Meeting |
|:-------------------:|:-------:|
|     Yucheng Zhu     |    1    |
|    Songpeng Liu     |    2    |
|    Xiangji Kong     |    3    |
|     Shao An Tay     |    4    |
