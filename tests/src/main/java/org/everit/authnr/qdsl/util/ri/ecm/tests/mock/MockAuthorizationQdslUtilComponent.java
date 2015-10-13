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
package org.everit.authnr.qdsl.util.ri.ecm.tests.mock;

import org.everit.authnr.qdsl.util.ri.ecm.tests.AuthnrQdslUtilTest;
import org.everit.authorization.qdsl.util.AuthorizationQdslUtil;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Service;
import org.everit.osgi.ecm.extender.ECMExtenderConstants;
import org.junit.Assert;

import com.mysema.query.types.ConstantImpl;
import com.mysema.query.types.Expression;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.template.BooleanTemplate;

import aQute.bnd.annotation.headers.ProvideCapability;

/**
 * Mock AuthorizationQdslUtil.
 */
@Component(componentId = "MockAuthorizationQdslUtilComponent",
    configurationPolicy = ConfigurationPolicy.OPTIONAL)
@ProvideCapability(ns = ECMExtenderConstants.CAPABILITY_NS_COMPONENT,
    value = ECMExtenderConstants.CAPABILITY_ATTR_CLASS + "=${@class}")
@Service
public class MockAuthorizationQdslUtilComponent implements AuthorizationQdslUtil {

  public static final BooleanExpression AUTHORIZATION_PREDICATE = BooleanTemplate.FALSE;

  public static final Expression<Long> TARGET_RESOURCE_ID_EXPRESSION =
      ConstantImpl.create(AuthnrQdslUtilTest.TARGET_RESOURCE_ID);

  @Override
  public BooleanExpression authorizationPredicate(final long authorizedResourceId,
      final Expression<Long> targetResourceId, final String... actions) {
    Assert.assertEquals(MockAuthenticationContextComponent.CURRENT_RESOURCE_ID,
        authorizedResourceId);
    Assert.assertEquals(TARGET_RESOURCE_ID_EXPRESSION, targetResourceId);
    Assert.assertArrayEquals(AuthnrQdslUtilTest.getActions(), actions);
    return AUTHORIZATION_PREDICATE;
  }

}
