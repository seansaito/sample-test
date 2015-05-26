#Refactoring, improvement to the specifications of the App

##Database
- Create separate tables for students and companies. This is because students and companies are inherently different entities, and thus the table would be required to hold different kinds of information for each. For example, the students table will also record the student's school, age, phone number, etc., and the companies table will have information about the company's size, location, etc.

##Events
- More information, including end time and location, as well as a link to the event
  page.
- Create tags for the events so that 1) they will be grouped by category, and 2)
  students can find events of their interest more easily.
