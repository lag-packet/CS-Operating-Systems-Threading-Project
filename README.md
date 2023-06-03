---
# CS Operating Systems Threading Project
This repository contains a simulation of a multithreaded manufacturing process for a hypothetical product called a "bunny." The simulation models the entire manufacturing process, from the production of individual parts to the assembly of the final product.

## Project Overview
This simulation models a manufacturing process where different parts of a product are produced by different manufacturers and assembled on an assembly line. The simulation uses multithreading to manage the various components of the manufacturing process, including the manufacturers, trucks, and assembly line.

### Components

- `Main.java` - This class initializes the program, setting up the assembly line, trucks, and manufacturers. Users are prompted to input the number of deliveries to be made.

- `AssemblyLine.java` - This class represents an assembly line for the product. It maintains a queue of deliveries from trucks, and each delivery contains a number of parts. The assembly line simulates the process of receiving parts from trucks and assembling products.

- `Manufacturer.java` - This class represents a manufacturer that produces a specific type of part. Each manufacturer is associated with a truck, which it loads with parts. The manufacturer simulates the process of manufacturing parts and loading them onto its truck.

- `Truck.java` - This class represents a truck associated with a specific manufacturer. The truck simulates the process of receiving parts from its manufacturer and delivering them to the assembly line.

## Academic Honesty / Usage Policy
This repository is a part of a school project and is made public for the purpose of showcasing my course work. The project is not intended for use in future school projects. Any use of this code for academic purposes without proper citation is considered a breach of academic integrity rules at most institutions.

Please note that plagiarism is a serious offense and can result in severe consequences. This includes submitting this project as your own work or substantially incorporating it into your own project without proper acknowledgment. Before using any materials from this project, please consult your institution's academic honesty policy or your instructor.

If you wish to reference or use this project in any way, please ensure to provide proper attribution. This can usually be done in the form of a citation or a noticeable comment in your code.

Remember, the goal of school projects is to learn and develop your own skills..
---
