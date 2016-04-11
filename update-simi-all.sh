cd /data/src/meijia-parent/
svn up
mvn clean package
/data/tomcat/bin/shutdown.sh
sleep 1
rm -rf /data/tomcat/webapps/simi
\cp -rf /data/src/meijia-parent/simi-app/target/simi.war /data/tomcat/webapps/

rm -rf /data/tomcat/webapps/simi-oa
\cp -rf /data/src/meijia-parent/simi-oa/target/simi-oa.war /data/tomcat/webapps/

rm -rf /data/tomcat/webapps/xcloud
\cp -rf /data/src/meijia-parent/xcloud/target/xcloud.war /data/tomcat/webapps/

/data/tomcat/bin/startup.sh

cd /data/tomcat/webapps/simi-h5
svn up

