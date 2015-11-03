cd /data/src/meijia-parent/
svn up
mvn clean install package -pl simi-oa -am
/data/tomcat/bin/shutdown.sh
sleep 1
\cp -rf /data/src/meijia-parent/simi-oa/target/simi.war /data/tomcat/webapps/
/data/tomcat/bin/startup.sh