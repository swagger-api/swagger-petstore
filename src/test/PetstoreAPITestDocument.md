# Petstore API Tests

This document describes the test cases for the Petstore API. Each test case is classified by **criticality** and **severity**.

## Table of Contents
- [Pets API Tests](#pets-api-tests)
- [Store API Tests](#store-api-tests)
- [User API Tests](#user-api-tests)
- [Bug Tests](#bug-tests)

---

## Pets API Tests

### 1. **Test: Add Pet**
   - **Description**: Tests the addition of a new pet.
   - **Criticality**: High - Essential functionality of adding pets.
   - **Severity**: Critical - Failure results in inability to add pets.
   - **Test Method**: `testAddPet`

### 2. **Test: Get Pet by ID**
   - **Description**: Retrieves a pet by its ID.
   - **Criticality**: High - A primary feature for fetching pet details.
   - **Severity**: Major - Failure blocks the retrieval of specific pets.
   - **Test Method**: `testGetPetById`

### 3. **Test: Update Pet**
   - **Description**: Updates the details of an existing pet.
   - **Criticality**: High - Users must be able to update pet details.
   - **Severity**: Critical - Failure results in incorrect or missing pet data.
   - **Test Method**: `testUpdatePet`

### 4. **Test: Delete Pet**
   - **Description**: Deletes a pet by its ID.
   - **Criticality**: Medium - Required for removing pets from the store.
   - **Severity**: Major - Failure results in the inability to delete pets.
   - **Test Method**: `testDeletePet`

### 5. **Test: Find Pets by Status**
   - **Description**: Finds pets by their status (e.g., available, sold).
   - **Criticality**: Medium - Helps filter pets for customers.
   - **Severity**: Moderate - Failure impacts search functionality but has workarounds.
   - **Test Method**: `testFindPetsByStatus`

### 6. **Test: Find Pets by Tags**
   - **Description**: Finds pets by tags.
   - **Criticality**: Low - An auxiliary feature to filter pets.
   - **Severity**: Minor - Failure would affect convenience but not core functionality.
   - **Test Method**: `test_06_FindPetsByTags`

---

## Store API Tests

### 7. **Test: Get Inventory**
   - **Description**: Retrieves the store's inventory.
   - **Criticality**: Medium - Essential for tracking available pets.
   - **Severity**: Major - Failure impacts stock management and business operations.
   - **Test Method**: `testGetInventory`

### 8. **Test: Place Order**
   - **Description**: Places an order for a pet.
   - **Criticality**: High - A primary business operation.
   - **Severity**: Critical - Failure prevents customers from purchasing pets.
   - **Test Method**: `testPlaceOrder`

### 9. **Test: Get Order by ID**
   - **Description**: Retrieves an order by its ID.
   - **Criticality**: Medium - Important for tracking orders.
   - **Severity**: Major - Failure makes it difficult to monitor specific orders.
   - **Test Method**: `testGetOrderById`

### 10. **Test: Delete Order**
   - **Description**: Deletes an order by its ID.
   - **Criticality**: Medium - Needed for order cancellations.
   - **Severity**: Major - Failure causes order retention issues.
   - **Test Method**: `testDeleteOrder`

---

## User API Tests

### 11. **Test: Create User**
   - **Description**: Creates a new user.
   - **Criticality**: High - Crucial for customer management.
   - **Severity**: Critical - Failure stops user creation and system usability.
   - **Test Method**: `testCreateUser`

### 12. **Test: Get User by Username**
   - **Description**: Retrieves user details by username.
   - **Criticality**: High - Essential for retrieving user information.
   - **Severity**: Major - Failure results in inability to fetch user data.
   - **Test Method**: `testGetUserByUsername`

### 13. **Test: Update User**
   - **Description**: Updates an existing user's details.
   - **Criticality**: Medium - Important for keeping user information up to date.
   - **Severity**: Major - Failure causes incorrect user data in the system.
   - **Test Method**: `testUpdateUser`

### 14. **Test: Delete User**
   - **Description**: Deletes a user by username.
   - **Criticality**: Medium - Needed for account management.
   - **Severity**: Major - Failure results in retained user accounts.
   - **Test Method**: `testDeleteUser`

### 15. **Test: Login User**
   - **Description**: Logs in a user using username and password.
   - **Criticality**: High - Required for user authentication.
   - **Severity**: Critical - Failure results in inability to access user accounts.
   - **Test Method**: `testLoginUser`

### 16. **Test: Logout User**
   - **Description**: Logs out the current user.
   - **Criticality**: Medium - A security feature for account sessions.
   - **Severity**: Minor - Failure impacts session management but not core functionality.
   - **Test Method**: `testLogoutUser`

---

## Bug Tests

### 17. **Test: Login Missing User and Password**
   - **Description**: Tests login when username and password are missing.
   - **Criticality**: High - Bug test for correct error handling.
   - **Severity**: Critical - Failure means insecure login behavior.
   - **Test Method**: `testLogin_MissingUserAndPassword`

### 18. **Test: Login Invalid User and Password**
   - **Description**: Tests login with empty username and password.
   - **Criticality**: High - Ensures validation for login credentials.
   - **Severity**: Critical - Failure results in insecure login behavior.
   - **Test Method**: `testLogin_InvalidUserAndPassword`

### 19. **Test: Create User Missing Username**
   - **Description**: Tests user creation without a username.
   - **Criticality**: High - Validates the requirement for usernames.
   - **Severity**: Critical - Failure leads to invalid user creation.
   - **Test Method**: `testCreateUser_missingUsername`

### 20. **Test: Create User Missing Password**
   - **Description**: Tests user creation without a password.
   - **Criticality**: High - Ensures password validation.
   - **Severity**: Critical - Failure leads to invalid user creation and security issues.
   - **Test Method**: `testCreateUser_missingPassword`
