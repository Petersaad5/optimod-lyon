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
3. **Manual Request Addition:** Users can manually add new delivery requests by selecting the locations directly on the map.
4. **Optimal Path Calculation:** The app computes the best routes for each courier to handle their deliveries efficiently, depending on the number of available couriers.
5. **Data Parsing:** Delivery requests and map data are loaded from an XML file every time the app starts, and then saved in the Data Class.

---

## How It Works

1. **Input Data:**
   - The user specifies the number of couriers.
   - Delivery requests and map details are read from the XML files also chosen from the user.
2. **Manual Adjustments:**
   - Users can add new delivery requests directly through the app. The requests will be saved in the Data class.
3. **Optimal Path Output:**
   - The app computes and displays the optimal route for each courier, considering all requests and durations.

---

## Input Format

- **Delivery Requests:**
  - The requests are loaded from the XML file chosen by the user.
  - The user can input a new Delivery request by indicating the Pickup location, the Destination, the Pickup duration, and the Delivery duration
- **Map Data:**
  - The roads and the intersections are loaded from the XML file to create the map.
---

## Purpose

Optimod'Lyon simplifies delivery planning by providing:

- Efficient routing for couriers depending on the number of available couriers
- User-friendly management of delivery requests
- Flexible input options for real-world scenarios
