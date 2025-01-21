key pair-kind of authentication that u are acessing your computer

key oair file is use for login in computer
http-acess web page over internet

ssh-acess computer over internet,can login to remote computer

by amazon public ip u cana cess website

# EC2
can acess it from anywhere
![alt text](image.png)
![alt text](image-1.png)
![alt text](image-2.png)
![alt text](image-3.png)
Now connect to this computer,click on checkbox and then connect and go to shs client tab and follow steps given
![alt text](image-4.png)
run all command in git bash where the file is present
![alt text](image-5.png)
now install python and run ab server flask
https://flask.palletsprojects.com/en/stable/installation/
 1) mkdir myproject//ls -l  command to see information about files and directory in present folder
1) cd myproject
2) sudo apt-get update
3)sudo apt-get install python3 then sudo apt-get install python3-venv
4) python3 -m venv .venv
5) . .venv/bin/activate
6) pip install Flask

![alt text](image-6.png)

You should run sudo apt update whenever you want to install new software or check for updates. It ensures that your system has the most recent information about available packages. Without running this command, your package manager might not know about the latest versions, leading to outdated software being installed.

# Now create simple flask application
https://flask.palletsprojects.com/en/stable/quickstart/
create a file first by command in same git bash
1. nano hello.py
![alt text](image-7.png)
paste it 
from flask import Flask

app = Flask(__name__)

@app.route("/")
def hello_world():
    return "<p>Hello, World!</p>"

Now execute code option-ctrl +x and save file give Y
7)  flask --app hello run --host=0.0.0.0

![alt text](image-8.png)
Now in browser go to aws ec2 publicip:port that is 65.0.61.40:5000
By default awsfor security pupose block all of the port of this machine ,it only allow by ssh but we want to connect via http on port 5000
so go to security and give inbound rule in security group
![alt text](image-9.png)
clcik on security group and edit inbound rules
![alt text](image-10.png)
![alt text](image-11.png)
 








