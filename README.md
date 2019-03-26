# Prometheus

https://github.com/VitalGolub/Prometheus/  
  
Instructions:  
The project uses gradle, you can either create a runnable to jar to run the game or I created a seperate runClient task and runServer taskthat you can use. Make sure to run the server FIRST before the cleint. If you are using eclipse you can simply open the task view, open the build tasks and double click runServer and then double click runClient. You can also manually run the main classes as well.  
  
Through gradle, "gradle runServer runClient"

Members:  
Atharva Shinde  
Gagan Pabla  
Lucas Simone  
Vital Golub  
  
Contributions:  
Vital Golub - Movement, Projectiles, collision, FileIO(PlayerStats, and leaderboards), SocketIO(Stat saving by server), interfaces, gradle    
Lucas Simone - Mob design(Wizard, Runner), Level/Map design(Walls, wave system, map progress), image work, balancing and bounding box work  
Gagan Pabla - Main menu, credits screen, leaderboard screen, end title screen, stats screen, created and worked with css style sheet  
Atharva Shinde - Sprites creation and implementation (Mob sprites, level sprites, static entity sprites, main player sprites), animations  
