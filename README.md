# Synchronizer with FireBird DataBase
_____________________________________
## This script has two methods used as parametres: pull and push. 
* Pull. This method will take data from DataBase (For our task it's a EMAIL and PASSWORD) and write it into the file "passwords.txt"
* Push. This method will take data from the file "passwords.txt" and fetch it into the DataBase. How it works: Fetched data is all data that was already in the DataBase but with changed passwod. If you will change Email or add one more record it won't be add to the DataBase
_______________________________________________________
## How to use this script:
1. You have to install Java SDK 12
2. Customize conf.cfg with your own properties
3. If you open this project in the IDE then you have to write "pull" or "push" in the Switch cunstuction. For example
```Java
switch ("push"){
    case "push":
        ...
    case "pull":
        ...
    default:
        ...
}
```
Alse you need to have conf.cfg and password.txt (for push) in the root folder.
4. If you want run this script from the terminal you have to the next:  open terminal in the "src" folder, then you have to drop the conf.cfg and password.txt (for push) file into this folder. Then write next strings:
    ```
        javac -g -cp jaybird.jar; check.java
        java -cp jaybird.jar; check PULL_OR_PUSH
    ```
___________________________________________________________
conf.cfg should look like this for correct work:
```
jdbc:firebirdsql://localhost/C:/Users/User/Downloads/mail.fdb?serverTimezone=Europe/Moscow&useSSL=false
SYSDBA
masterkey
```
First line is about url to connect to the server
Second line is about USERNAME 
Third line is about PASSWORD
__________________________________________________________
password.txt should look like this for the correct work:
```
Email: org1@pskovedu.ru; Password: password-1
Email: org2@pskovedu.ru; Password: password-2
Email: org3@pskovedu.ru; Password: password-3
Email: org4@pskovedu.ru; Password: password-4
Email: org5@pskovedu.ru; Password: password-5
```
**WARNING: YOU HAVE TO FOLLOW ALL THE RULES, ALL THE SPACES. OTHERWISE IT WOULDN'T WORK**