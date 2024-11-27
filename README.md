# Optimod'Lyon

## Overview

Optimod'Lyon is a delivery optimization application designed to streamline the delivery process for couriers. The app calculates the optimal delivery paths for couriers based on delivery requests and map data provided by the user. The goal is to ensure efficient task distribution and minimize delivery time.

---

## Features

1. **Courier Assignment:** Users can input the number of couriers available for delivery.
2. **Delivery Requests:** The app reads a list of delivery requests, including:
   - Pickup location
   - Destination
   - Pickup and delivery duration
3. **Manual Request Addition:** Users can manually add new delivery requests.
4. **Optimal Path Calculation:** The app computes the best routes for each courier to handle their deliveries efficiently.
5. **Data Parsing:** Delivery requests and map data are loaded from an XML file every time the app starts.

---

## How It Works

1. **Input Data:**
   - The user specifies the number of couriers.
   - Delivery requests and map details are read from an XML file.
2. **Manual Adjustments:**
   - Users can add new delivery requests directly through the app.
3. **Optimal Path Output:**
   - The app computes and displays the optimal route for each courier, considering all requests and durations.

---

## Input Format

- **Delivery Requests:**
  - Pickup location
  - Destination
  - Pickup duration
  - Delivery duration
- **Map Data:**
  - A connectivity map showing travel times between locations.

---

## Purpose

Optimod'Lyon simplifies delivery planning by providing:

- Efficient routing for couriers
- User-friendly management of delivery requests
- Flexible input options for real-world scenarios
