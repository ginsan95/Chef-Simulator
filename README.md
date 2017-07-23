# Chef-Simulator
## Introduction
This project is a chef simulation software to simulate the ordering and cooking process of a restaurant. Each order has different job length, where according to the shortest job next algorithm, cooking the order with the lowest job length will improve the performance of the overall job scheduling. However, to prevent starvation, I applied the priority job algorithm instead, where the shortest job will have the highest priority in getting executed by the chef. At the same time, when a new job comes into the queue, all the jobs inside the queue will have their priority increases which ensure that all jobs will get executed by the chef eventually. It is my assignment for the subject Data Structure and Algorithm during my undergraduate semester 6.

## Screenshots
![Screenshot 1](https://github.com/ginsan95/Chef-Simulator/blob/master/demo/screenshots/screenshot%201.jpg?raw=true)
![Screenshot 2](https://github.com/ginsan95/Chef-Simulator/blob/master/demo/screenshots/screenshot%202.jpg?raw=true)
