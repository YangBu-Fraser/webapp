echo "[INFO] Updating package list."
sudo apt-get update -y
sudo apt-get upgrade -y

echo "[INFO] Install necessary packages."
sudo apt install zip
sudo apt install openjdk-17-jdk
sudo apt install tomcat10
sudo apt install maven

echo "[INFO] Installing MySQL."
sudo apt-get install mysql-server -y
sudo systemctl start mysql
sudo systemctl enable mysql

sudo mysql -e "CREATE DATABASE csye6225;"
sudo mysql -e "CREATE USER 'csye6225'@'localhost' IDENTIFIED BY 'StrongP@ssw0rd!';"

sudo mysql -e "GRANT ALL PRIVILEGES ON csye6225.* to 'csye6225'@'localhost';"
sudo mysql -e "FLUSH PRIVILEGES;"

echo "[INFO] Create Linux group and user."
sudo groupadd csye6225group
sudo useradd -m -s /bin/bash -g csye6225group csye6225user

echo "[INFO] File Ops."
sudo mkdir -p /opt/csye6225

sudo unzip webapp.zip -d /opt/csye6225

echo "[INFO] Update permissions"
sudo chown -R csye6225user:csye6225group /opt/csye6225
sudo chmod -R 750 /opt/csye6225

echo "[INFO] All done !"
