# Team: CodeMages


## Team Meeting 1 - Week 6 - 2023-09-09  (2:00 pm-3:26 pm)
**Participants:**
- Songpeng Liu
- Xiangji Kong
- Yucheng Zhu

**Absent:** 

Shao An Tay was absent due to the incoming exam and assessment
- He will read the meeting minutes afterwards 

**Lead/scribe:** Yucheng Zhu

## Agreed Procedure
Stand up Procedure: 
- [Each team member briefly shares their progress and any roadblocks.]
- **Everyone:**
- We have read the document and prepared for this meeting.

- **Songpeng Liu:** 
- *Progress:*
- - Researched the instruction
- - ideated the process
- - Thinking about how to develop the Android app
- - Proposed app ideas and name

- **Xiangji Kong:**
- *Progress:*
- - Researched the guideline
- - Ideated the app attributes
- - Wrote the Team Conflict Resolution Guideline
- - Ideas about search attributes
- *Roadblocks:*
- - How to distribute the 4 Basic feature when two are easy difficulty but the other two are medium
- - How to distribute the 6 requirements to 4 people (maybe the next meeting)

- - The document does not mention GUI, GUI tests
- - Hiddle requirements unclear

- **Yucheng Zhu:**
- *Progress:*
- - Researched the guideline
- - Discussed and ideated app ideas with Songpeng Liu and Xiangji Kong
- - Researched the data source
- - Researched how to implement the data structure
- *Roadblocks:*
- - Lack of open source data
- - The criterion for "Excellent" "Data Structure" may be involved to implement

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

## Agenda Items
| Number |                                  Item |
|:-------|--------------------------------------:|
| 1      |                       Scrape the data |
| 2      |                  Architectural design |
| 3      |                            GUI design |
| 4      |                      Work allocations |
| 5      |                Discuss code ownership |
| 6      | Established roles and scribe rotation |

## Meeting Minutes
- [Summary of the progress reports, discussions, decisions, and matters to be confirmed from the meeting.]

- App topic and name
- How to get data
- Picture license 

- **Discussion:**
- *1. Copyright*:
- Can make a webpage to display Amazon's webpage
- Can use a link to Amazon to avoid copy right issues
- No picture

- *2. How to avoid copyright infringement in the future*:
- How to resolve copyright issues, especially unknowing violation?
- Solution: any member can notify this issue, and we can change the images

- *3. What to include in the data?*
- Author, Name, Year of Publication, Price, Page Number, From Which Seller  
- Easy and medium tasks.
- Easy task taker also take GUI and integration tests.

- *4. Should we use a database such as sqlite?*
- Implementation without sqlite:
- - Data: stored in Json/XML/spoke
- - `"Price=$1-10; Name='King Lear'; Date>2010"`
- - Grammar: convert string to objects
- - HashMap: Find items matching `Name='King Lear'`
- - Tree: sort the numerical data to get all results with `1<=price<=10`
- *Pros*:
- - more efficient
- - faster data access
- - easier to implement search
- *Cons*: does not meet the doc requirements:
- - 1) Tree search
- - 2) File: Json (used in the user profile)/XML (used in the user profile)/bespoke

- *A compromise reached:*
- Use sqlite to get the results for a more efficient implementation.
- The relevant results shall be tokenised and parsed using grammar
- the parsed data shall be stored in the tree and HashMap
- Then query results can be sorted again, and sort the displayed list to meet the tree requirement
- Read/write the customers' data to disk to meet the Json/Xml/bespoke data structure requirement

- *5. Assign code ownership*
- Why?
- To ensure that the codes meet the requirements and no criteria are missed
- To assign each code to people specialise in this field to ensure high quality code 
- - and architecturally sensible structure
- To enable code review


- **Matters to be confirmed from the meeting:**
- Everyone will check with his timetable and confirm sprint 1 schedule.
- Check the allocated tasks with Shao An Tay when he returns.


## Work Allocations
- Shao An Tay: Search, Grammar and Integration test
- Songpeng Liu: LoadShowData, DataFiles and Data Profile
- Xiangji Kong: Login, UI and UI-Layout
- Yucheng Zhu: Data Scraping and Trees (AVL- and B-tree with deletion and all functions)

## Action Items
|                 Task                 |          Assigned To           |              Due Date               |
|:------------------------------------:|:------------------------------:|:-----------------------------------:|
|        Skeleton and docstring        |          Xiangji Kong          |      End of mid-semester break      |
|            Login (Basic)             |          Xiangji Kong          |               Week 8                |
|                 GUI                  |          Xiangji Kong          |               Week 8                |
|         UI-Layout (Customer)         |          Xiangji Kong          |       Decided before Sprint 2       |
|        Skeleton and docstring        |          Songpeng Liu          |      End of mid-semester break      |
|         LoadShowData (Basic)         |          Songpeng Liu          |               Week 8                |
|          DataFiles (Basic)           |          Songpeng Liu          |               Week 8                |
|       Data Profile (Customer)        |          Songpeng Liu          |       Decided before Sprint 2       |
|       Search-Filter (Customer)       |    Decided before Sprint 2     |       Decided before Sprint 2       |
|        Skeleton and docstring        |          Yucheng Zhu           |      End of mid-semester break      |
| Data Scraping (Python BeautifulSoup) |          Yucheng Zhu           |      End of mid-semester break      |
|           Tree (AVL tree)            |          Yucheng Zhu           |      End of mid-semester break      |
|            Tree (B-tree)             |          Yucheng Zhu           |       Decided before Sprint 2       |
| Data-Deletion (AVL tree) (Customer)  |          Yucheng Zhu           |               Week 7                |
|  Data-Deletion (B-tree) (Customer)   |          Yucheng Zhu           |       Decided before Sprint 2       |
|        Skeleton and docstring        |          Shao An Tay?          |      End of mid-semester break      |
|            Search (Basic)            |          Shao An Tay?          |       Decided before Sprint 2       |
|               Grammar                |          Shao An Tay?          |               Week 8                |
|          Integration tests           |          Shao An Tay?          |       Decided before Sprint 2       |


## Owner (Check that our code meets the criteria)
- Report: Yucheng Zhu
- Teamwork: Yucheng Zhu
- Data Structures: Songpeng Liu
- Code Quality and Organization: Liu 
- User Interface / User Experience (UI/UX): Xiangji Kong
- Testing: Shao An Tay?
- Creativity: Shao An Tay?

## App Idea:
Book Price Comparison App

- Our members proposed four ideas:
- 1. Game Finding App
- 2. Car Searching App
- 3. Hotel Booking App
- 4. Book Finder

We discussed the following consideration:
- The requirement to use a tree data structure to sort
- The requirement to store data in a Json/Xml/bespoke file format
- The availability of data
- - Feasibility of random data
- - Open source dataset such as from Kaggle
- - Easiness of scraping data
- The copyright or license of the data
- The visual appeal of the app given our constraints
- The requirement to use the app in the real-world application

We reached a consensus that a Book Price Comparison App is best suited for the above considerations:
- It also has the potential to help real-world user to buy books from the cheapest seller
- Data is easy to scrape
- Using real, scraped data that can be regularly updated/re-scraped
- Prices and years are sortable with a tree
- Data or user profiles can be stored in a Json/Xml/bespoke file format

## App Name:
BookPlaza

- We brainstormed a dozen name
- BookPlaza is
- 1. Concise
- 2. Fits the app idea
- 3. Memorable
- 4. Implies the comprehensiveness of our data and power of our search capability

## Scribe Rotation
The following dictates who will scribe in this and the next meeting.

|     Name     | Meeting |
|:------------:|:-------:|
| Yucheng Zhu  |    1    |
| Songpeng Liu |    2    |
| Xiangji Kong |    3    |
|     Jay      |    4    |
