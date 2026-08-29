FROM quay.io/wildfly/wildfly:37.0.1.Final-jdk21

COPY build/libs/ROOT.war /opt/jboss/wildfly/standalone/deployments/

RUN /opt/jboss/wildfly/bin/add-user.sh -u admin -p admin --silent

ENTRYPOINT ["/opt/jboss/wildfly/bin/standalone.sh", \
            "-b", "0.0.0.0", \
            "-bmanagement", "0.0.0.0", \
            "-Djboss.http.proxy-address-forwarding=true"]

EXPOSE 8080 9990