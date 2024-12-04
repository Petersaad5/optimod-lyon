package com.optimodlyon.optimodlyon.utils;

import com.optimodlyon.optimodlyon.model.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


public class Parser {
    public static Map<Long, IntersectionModel> intersectionMap = new HashMap<>();

    // Create global data object to store the parsed data
    public static Data data = new Data();

    public static void parsePlan(File file) {
        List<IntersectionModel> intersections = new ArrayList<>();
        List<RoadModel> roads = new ArrayList<>();

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
                    IntersectionModel intersection = new IntersectionModel(id, latitude, longitude);
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
                    IntersectionModel origin = intersectionMap.get(Long.parseLong(element.getAttribute("origine")));
                    IntersectionModel destination = intersectionMap.get(Long.parseLong(element.getAttribute("destination")));
                    String name = element.getAttribute("nomRue");

                    // create the object
                    RoadModel road = new RoadModel(length, origin, destination, name);
                    roads.add(road);
                }
            }

            // Set the parsed intersections and roads in the data object
            data.setIntersections(intersections);
            data.setRoads(roads);
            data.setMap(new MapModel(1, intersections, roads));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void parseDemande(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            // Parse <entrepot> element
            NodeList warehouseList = document.getElementsByTagName("entrepot");
            WarehouseModel warehouse = null;
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
                    IntersectionModel address = intersectionMap.get(adresse);
//                    if (address == null) {
//                        address = new IntersectionModel(adresse);
//                        intersectionMap.put(adresse, address);
//                    }

                    // Create the WarehouseModel object
                    warehouse = new WarehouseModel(heureDepart, address);
                }
            }

            // Create the DeliveryRequestModel object
            DeliveryRequestModel deliveryRequest = new DeliveryRequestModel();
            deliveryRequest.setWarehouse(warehouse);
            deliveryRequest.setId(1L); //TODO: set the id so its not null
            deliveryRequest.setDeliveries(new ArrayList<>());

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
                    IntersectionModel originIntersection = intersectionMap.get(origine);
//                    if (originIntersection == null) {
//                        originIntersection = new IntersectionModel(origine);
//                        intersectionMap.put(origine, originIntersection);
//                    }

                    IntersectionModel destinationIntersection = intersectionMap.get(destination);
//                    if (destinationIntersection == null) {
//                        destinationIntersection = new IntersectionModel(destination);
//                        intersectionMap.put(destination, destinationIntersection);
//                    }

                    // Create the DeliveryModel object
                    DeliveryModel delivery = new DeliveryModel(dureeLivraison, dureeEnlevement, destinationIntersection, originIntersection);
                    deliveryRequest.getDeliveries().add(delivery);
                }
            }

            // Set the parsed delivery request in the data object
            data.setDeliveryRequest(deliveryRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}