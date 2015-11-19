cd /data/src/meijia-parent/
svn up
cd /data/src/meijia-parent/simi-app
mvn clean package
/data/tomcat/bin/shutdown.sh
sleep 1
rm -rf /data/tomcat/webapps/simi
\cp -rf /data/src/meijia-parent/simi-app/target/simi.war /data/tomcat/webapps/
/data/tomcat/bin/startup.sh

