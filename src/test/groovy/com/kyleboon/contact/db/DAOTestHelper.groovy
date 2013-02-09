package com.kyleboon.contact.db

import ch.qos.logback.classic.Level
import com.yammer.dropwizard.config.Configuration
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.config.LoggingConfiguration
import com.yammer.dropwizard.config.LoggingFactory
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.SessionFactoryFactory
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import spock.lang.Shared
import spock.lang.Specification

abstract class DAOTestHelper extends Specification {

    @Shared SessionFactory sessionFactory
    Session session
    Transaction transaction

    void setupSpec() {
        buildSessionFactory()
    }

    void setup() {
        session = sessionFactory.currentSession
        transaction = session.beginTransaction()
    }

    void cleanup() {
        session?.flush()
        session?.clear()
        transaction?.rollback()

        session = null
        transaction = null
    }

    void cleanupSpec() {
        sessionFactory = null
    }

    abstract List<Class<?>> getEntities();

    DatabaseConfiguration getDatabaseConfiguration() {
        def properties = ['hibernate.current_session_context_class': 'thread',
                'hibernate.show_sql': 'false',
                'hibernate.generate_statistics': 'false',
                'hibernate.use_sql_comments': 'false',
                'hibernate.hbm2ddl.auto': 'create-drop']
        return new DatabaseConfiguration(driverClass: 'org.h2.Driver',
                user: 'sa', password: 'sa',
                url: 'jdbc:h2:etl_enrollment',
                properties: properties)
    }

    private void buildSessionFactory() {
        if (!getEntities()) {
            throw new Exception('no entities provided. Define list of entities in your test class.')
        }
        LoggingConfiguration.ConsoleConfiguration consoleConfiguration = new LoggingConfiguration.ConsoleConfiguration()
        consoleConfiguration.setThreshold(Level.INFO)
        LoggingConfiguration loggingConfiguration = new LoggingConfiguration(consoleConfiguration: consoleConfiguration)
        loggingConfiguration.setLoggers(['org.hibernate': Level.INFO])

        Configuration configuration = new Configuration(loggingConfiguration: loggingConfiguration)
        new LoggingFactory(configuration.getLoggingConfiguration(),'DAOTest').configure()
        SessionFactoryFactory factory = new SessionFactoryFactory()
        Environment environment = new Environment('DAOTest', configuration, null, null)
        this.sessionFactory = factory.build(environment, databaseConfiguration, entities)
    }
}