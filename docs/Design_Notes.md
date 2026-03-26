# Design Notes

## Why `ArrayList` instead of array?
`ArrayList` was used because the number of students, courses, and enrollments can grow during program execution. Arrays have a fixed size, so they are less convenient for a menu-driven system where the user keeps adding data.

`ArrayList` also makes the code easier to read because we can use methods like `add`, `remove`, and `size` directly without manually resizing storage.

## Where static members were used and why
Static members were used in `IdGenerator`:
- `studentIdCounter`
- `courseIdCounter`
- `enrollmentIdCounter`
- `getNextStudentId()`
- `getNextCourseId()`
- `getNextEnrollmentId()`

They are static because ID generation belongs to the class itself, not to one object instance. All services should share the same counters.

## Where inheritance was used and what it gave us
Inheritance is used in:
- `Person` -> base class
- `Student extends Person`
- `Trainer extends Person`

This avoids duplicating common fields like `id`, `firstName`, `lastName`, and `email`. It also helps show method overriding through `getDisplayName()`.

For example, `Student` adds batch information to the display name. This gives a simple introduction to polymorphism because the child class can provide behavior that is more specific than the parent class.

## Separation of concerns
The project is divided into layers:
- `entity` for data models
- `service` for business logic and list management
- `ui` for console interaction
- `util` for reusable helpers
- `exception` for custom exception types

This keeps the code cleaner and easier to understand for beginners.

