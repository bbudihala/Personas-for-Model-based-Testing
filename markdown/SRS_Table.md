# Customer Profile Management SRS-FC01

| Requirement ID  | **SRS-FC01 (Customer Profile Management)** |
|----------------|-------------------------------------------|
| **Objective**  | Test the Square Customers API's ability to manage customer profiles, including the basic CRUD customer flows. |
| **Preconditions** | 1. The testing environment is set up with the necessary permissions to access the Customers API. |
| **Postconditions** | 1. All customer records created during testing are deleted to ensure data cleanliness.<br>2. API responses are recorded for verification purposes. |

## Main Flow

| Step | Action | Outcome |
|------|--------|---------|
| **Step 1: Create customer** | Send a `POST` request to `/v2/customers` with complete customer details. | API creates the customer and returns `200 OK` with customer ID. |
| **Step 2: Retrieve customer** | Use the customer ID to send a `GET` request to `/v2/customers/{customer_id}`. | API retrieves customer details, returning `200 OK`. |
| **Step 3: Update customer** | Send a `PUT` request to `/v2/customers/{customer_id}` with updated fields. | API updates customer info and returns `200 OK`. |
| **Step 4: Delete customer** | Send a `DELETE` request to `/v2/customers/{customer_id}`. | API deletes the customer and returns `200 OK`. |
| **Step 5: List all customers** | Send a `GET` request to `/v2/customers`. | API returns `200 OK` with a list of customers, excluding deleted ones. |

## Alternative Flows

| Scenario | Description |
|----------|-------------|
| **Verify delete and update integrity** | After deleting a customer, attempting to update them should return `404 Not Found`. Listing all customers should confirm their absence. |

## Exceptions

| Scenario | Description |
|----------|-------------|
| **Handling invalid inputs** | Sending a `POST` request with missing/incorrect data should return `400 Bad Request` with an error message. |

---

<br><br><br>

# Customer Loyalty Enrollment SRS-FC02

| Requirement ID  | **SRS-FC02 (Customer Loyalty Enrollment)** |
|----------------|-------------------------------------------|
| **Objective**  | Validate the integrated functionality of the loyalty feature by enrolling customers, tracking their points accumulation, and rewarding them based on loyalty points milestones. |
| **Preconditions** | 1. The testing environment is set up with the necessary permissions to access the Customers API.<br>2. A test customer profile has been set up with no prior loyalty or gift card rewards issued.<br>3. A test loyalty program has been set up in a clean state. |
| **Postconditions** | 1. All loyalty records created during testing are deleted to ensure data cleanliness.<br>2. API responses are recorded for verification purposes. |

## Main Flow

| Step | Action | Outcome |
|------|--------|---------|
| **Step 1: Create loyalty account** | Send a `POST` request to `/v2/loyalty/accounts` to create a loyalty account and add it to an existing loyalty program. | API creates the account and returns `200 OK` with the loyalty account details. |
| **Step 2: Retrieve loyalty account** | Use the loyalty account ID to send a `GET` request to `/v2/loyalty/accounts/{account_id}`. | API retrieves loyalty account details, returning `200 OK`. |
| **Step 3: Accumulate loyalty points** | Use the loyalty account ID to send a `POST` request to `/v2/loyalty/accounts/{account_id}/accumulate` to add points from a purchase. | API confirms point accumulation with `200 OK`. |
| **Step 4: Search loyalty events** | Send a `POST` request to `/v2/loyalty/events/search` to check events related to the accumulated points. | API returns a list of events and confirms with `200 OK`. |
| **Step 5: Create loyalty reward** | Use the loyalty account ID to send a `POST` request to `/v2/loyalty/rewards` to issue a reward upon reaching a milestone. | API returns `200 OK` with reward details. |
| **Step 6: Retrieve loyalty reward** | Use the reward ID in a `GET` request to `/v2/loyalty/rewards/{reward_id}`. | API returns `200 OK` with reward details. |
| **Step 7: Redeem loyalty reward** | Use the reward ID and send a `POST` request to `/v2/loyalty/rewards/{reward_id}/redeem`. | API confirms redemption with `200 OK` and updates the reward status. |

## Alternative Flows

| Scenario | Description |
|----------|-------------|
| **Create loyalty reward with insufficient points** | Attempting to create a reward without enough points should return `400 Bad Request`. |
| **Verify delete and redeem integrity** | After deleting a reward, searching for it should reflect deletion, and attempting to redeem should return `404 Not Found`. |

## Exceptions

| Scenario | Description |
|----------|-------------|
| **Handling invalid inputs** | Sending a `POST` request with missing fields or negative values should return `400 Bad Request` with a descriptive error. |

---