cd /data/src/meijia-parent/
svn up
mvn clean package
/data/tomcat/bin/shutdown.sh
sleep 1
rm -rf /data/tomcat/webapps/simi-oa
\cp -rf /data/src/meijia-parent/simi-oa/target/simi-oa.war /data/tomcat/webapps/
/data/tomcat/bin/startup.sh