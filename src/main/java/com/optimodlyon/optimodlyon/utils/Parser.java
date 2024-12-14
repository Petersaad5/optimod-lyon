package com.optimodlyon.optimodlyon.utils;

import com.optimodlyon.optimodlyon.model.*;
import com.optimodlyon.optimodlyon.model.Map;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


public class Parser {
    public static java.util.Map<Long, Intersection> intersectionMap = new HashMap<>();


    public static Data parsePlan(File file, Data data) {
        List<Intersection> intersections = new ArrayList<>();
        List<Road> roads = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            // parse <noeud> elements
            NodeList nodeList = document.getElementsByTagName("noeud");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Long id = Long.parseLong(element.getAttribute("id"));
                    double latitude = Double.parseDouble(element.getAttribute("latitude"));
                    double longitude = Double.parseDouble(element.getAttribute("longitude"));

                    // create the object and store it in the map
                    Intersection intersection = new Intersection(id, latitude, longitude);
                    intersectionMap.put(id, intersection);
                    intersections.add(intersection);
                }
            }

            // parse <troncon> elements
            NodeList roadList = document.getElementsByTagName("troncon");
            for (int i = 0; i < roadList.getLength(); i++) {
                Node node = roadList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    double length = Double.parseDouble(element.getAttribute("longueur"));
                    Intersection origin = intersectionMap.get(Long.parseLong(element.getAttribute("origine")));
                    Intersection destination = intersectionMap.get(Long.parseLong(element.getAttribute("destination")));
                    String name = element.getAttribute("nomRue");

                    // create the object
                    Road road = new Road(length, origin, destination, name);
                    roads.add(road);
                }
            }

            // Set the parsed intersections and roads in the data object
            data.setIntersections(intersections);
            data.setRoads(roads);
            data.setMap(new Map(1, intersections, roads));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    public static List<Tour> parseDemande(File file, List<Courier> couriers, List<Delivery> deliveriesAdded) {
        List<Tour> toursWithoutTSP = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            // Parse <entrepot> element
            NodeList warehouseList = document.getElementsByTagName("entrepot");
            Warehouse warehouse = null;
            for (int i = 0; i < warehouseList.getLength(); i++) {
                Node node = warehouseList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    long adresse = Long.parseLong(element.getAttribute("adresse"));
                    String heureDepartStr = element.getAttribute("heureDepart");

                    // Parse heureDepart and create Date object
                    SimpleDateFormat dateFormat = new SimpleDateFormat("H:m:s");
                    Date heureDepart = dateFormat.parse(heureDepartStr);

                    // Retrieve the IntersectionModel using the id
                    Intersection address = intersectionMap.get(adresse);
//                    if (address == null) {
//                        address = new IntersectionModel(adresse);
//                        intersectionMap.put(adresse, address);
//                    }

                    // Create the WarehouseModel object
                    warehouse = new Warehouse(heureDepart, address);
                }
            }

            for(int i = 0; i < couriers.size(); i++) {
                // Create the DeliveryRequestModel object
                Tour tour = new Tour();
                DeliveryRequest deliveryRequest = new DeliveryRequest();
                deliveryRequest.setWarehouse(warehouse);
                deliveryRequest.setDeliveries(new ArrayList<>());
                deliveryRequest.setCourier(couriers.get(i));
                tour.setDeliveryRequest(deliveryRequest);
                toursWithoutTSP.add(tour);
            }

            // Parse <livraison> elements
            NodeList deliveryList = document.getElementsByTagName("livraison");
            for (int i = 0; i < deliveryList.getLength(); i++) {
                Node node = deliveryList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    long origine = Long.parseLong(element.getAttribute("adresseEnlevement"));
                    long destination = Long.parseLong(element.getAttribute("adresseLivraison"));
                    int dureeEnlevement = Integer.parseInt(element.getAttribute("dureeEnlevement"));
                    int dureeLivraison = Integer.parseInt(element.getAttribute("dureeLivraison"));

                    // Retrieve the IntersectionModel using the id
                    Intersection originIntersection = intersectionMap.get(origine);
//                    if (originIntersection == null) {
//                        originIntersection = new IntersectionModel(origine);
//                        intersectionMap.put(origine, originIntersection);
//                    }

                    Intersection destinationIntersection = intersectionMap.get(destination);
//                    if (destinationIntersection == null) {
//                        destinationIntersection = new IntersectionModel(destination);
//                        intersectionMap.put(destination, destinationIntersection);
//                    }

                    // Create the DeliveryModel object
                    Delivery delivery = new Delivery(dureeLivraison, dureeEnlevement, destinationIntersection, originIntersection);
                    int indexCourier = (i % couriers.size());
                    toursWithoutTSP.get(indexCourier).getDeliveryRequest().getDeliveries().add(delivery);
                }
            }

            for(int i = 0; i < deliveriesAdded.size(); i++) {
                int indexCourier = ((i+deliveryList.getLength() - 1) % couriers.size());
                toursWithoutTSP.get(indexCourier).getDeliveryRequest().getDeliveries().add(deliveriesAdded.get(i));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return toursWithoutTSP;
    }
}