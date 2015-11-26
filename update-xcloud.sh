cd /data/src/meijia-parent/
svn up
mvn clean package
/data/tomcat/bin/shutdown.sh
sleep 1
rm -rf /data/tomcat/webapps/xcloud
\cp -rf /data/src/meijia-parent/xcloud/target/xcloud.war /data/tomcat/webapps/
/data/tomcat/bin/startup.sh

