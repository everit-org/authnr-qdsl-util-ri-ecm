/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.authnr.qdsl.util.ri.ecm.tests;

import org.everit.authnr.qdsl.util.AuthnrQdslUtil;
import org.everit.authnr.qdsl.util.ri.ecm.tests.mock.MockAuthorizationQdslUtilComponent;
import org.everit.osgi.dev.testrunner.TestRunnerConstants;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Service;
import org.everit.osgi.ecm.annotation.ServiceRef;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.osgi.ecm.extender.ECMExtenderConstants;
import org.junit.Assert;
import org.junit.Test;

import aQute.bnd.annotation.headers.ProvideCapability;

/**
 * Test for AuthnrQdslUtil.
 */
@Component(componentId = "AuthnrQdslUtilTest", configurationPolicy = ConfigurationPolicy.OPTIONAL)
@ProvideCapability(ns = ECMExtenderConstants.CAPABILITY_NS_COMPONENT,
    value = ECMExtenderConstants.CAPABILITY_ATTR_CLASS + "=${@class}")
@StringAttributes({
    @StringAttribute(attributeId = TestRunnerConstants.SERVICE_PROPERTY_TESTRUNNER_ENGINE_TYPE,
        defaultValue = "junit4"),
    @StringAttribute(attributeId = TestRunnerConstants.SERVICE_PROPERTY_TEST_ID,
        defaultValue = "AuthnrQdslUtilTest") })
@Service(value = AuthnrQdslUtilTest.class)
public class AuthnrQdslUtilTest {

  private static final String[] ACTIONS = { "a1", "a2" };

  public static final long TARGET_RESOURCE_ID = 2;

  public static String[] getActions() {
    return ACTIONS.clone();
  }

  private AuthnrQdslUtil authnrQdslUtil;

  @ServiceRef(defaultValue = "")
  public void setAuthnrQdslUtil(final AuthnrQdslUtil authnrQdslUtil) {
    this.authnrQdslUtil = authnrQdslUtil;
  }

  @Test
  public void testComplex() {
    Assert.assertEquals(
        MockAuthorizationQdslUtilComponent.AUTHORIZATION_PREDICATE,
        authnrQdslUtil.authorizationPredicate(
            MockAuthorizationQdslUtilComponent.TARGET_RESOURCE_ID_EXPRESSION,
            AuthnrQdslUtilTest.getActions()));
  }

}
