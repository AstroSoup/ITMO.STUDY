FROM quay.io/wildfly/wildfly:37.0.1.Final-jdk21

COPY build/libs/ROOT.war /opt/jboss/wildfly/standalone/deployments/

ENTRYPOINT ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-Djboss.http.proxy-address-forwarding=true"]

EXPOSE 8080
