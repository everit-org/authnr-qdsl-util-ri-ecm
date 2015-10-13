/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.biz)
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
package org.everit.authnr.qdsl.util.ri.ecm.internal;

import java.util.Dictionary;
import java.util.Hashtable;

import org.everit.authentication.context.AuthenticationContext;
import org.everit.authnr.qdsl.util.AuthnrQdslUtil;
import org.everit.authnr.qdsl.util.ri.AuthnrQdslUtilImpl;
import org.everit.authnr.qdsl.util.ri.ecm.AuthnrQdslUtilConstants;
import org.everit.authorization.qdsl.util.AuthorizationQdslUtil;
import org.everit.osgi.ecm.annotation.Activate;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Deactivate;
import org.everit.osgi.ecm.annotation.ManualService;
import org.everit.osgi.ecm.annotation.ServiceRef;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.osgi.ecm.component.ComponentContext;
import org.everit.osgi.ecm.extender.ECMExtenderConstants;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;

import aQute.bnd.annotation.headers.ProvideCapability;

/**
 * ECM component for {@link AuthnrQdslUtil} * interface based on {@link AuthnrQdslUtilImpl}.
 */
@Component(componentId = AuthnrQdslUtilConstants.SERVICE_FACTORYPID,
    configurationPolicy = ConfigurationPolicy.FACTORY,
    label = "Everit Authenticated Authorization QDSL Util RI",
    description = "Component that registers an AuthnrQdslUtil OSGi service.")
@ProvideCapability(ns = ECMExtenderConstants.CAPABILITY_NS_COMPONENT,
    value = ECMExtenderConstants.CAPABILITY_ATTR_CLASS + "=${@class}")
@StringAttributes({
    @StringAttribute(attributeId = Constants.SERVICE_DESCRIPTION,
        defaultValue = AuthnrQdslUtilConstants.DEFAULT_SERVICE_DESCRIPTION,
        priority = AuthnrQdslUtilComponent.P1_SERVICE_DESCRIPTION, label = "Service Description",
        description = "The description of this component configuration. It is used to easily "
            + "identify the service registered by this component.") })
@ManualService(AuthnrQdslUtil.class)
public class AuthnrQdslUtilComponent {

  public static final int P1_SERVICE_DESCRIPTION = 1;

  public static final int P2_AUTHENTICATION_CONTEXT = 2;

  public static final int P3_AUTHORIZATION_QDSL_UTIL = 3;

  private AuthenticationContext authenticationContext;

  private AuthorizationQdslUtil authorizationQdslUtil;

  private ServiceRegistration<AuthnrQdslUtil> serviceRegistration;

  /**
   * Activate method of component.
   */
  @Activate
  public void activate(final ComponentContext<AuthnrQdslUtilComponent> componentContext) {
    AuthnrQdslUtilImpl authnrQdslUtil =
        new AuthnrQdslUtilImpl(authenticationContext, authorizationQdslUtil);

    Dictionary<String, Object> serviceProperties =
        new Hashtable<>(componentContext.getProperties());
    serviceRegistration = componentContext.registerService(AuthnrQdslUtil.class, authnrQdslUtil,
        serviceProperties);
  }

  /**
   * Component deactivate method.
   */
  @Deactivate
  public void deactivate() {
    if (serviceRegistration != null) {
      serviceRegistration.unregister();
    }
  }

  @ServiceRef(attributeId = AuthnrQdslUtilConstants.PROP_AUTHENTICATION_CONTEXT, defaultValue = "",
      attributePriority = P2_AUTHENTICATION_CONTEXT, label = "Authentication Context target",
      description = "OSGi service filter to identify the AuthenticationContext service.")
  public void setAuthenticationContext(final AuthenticationContext authenticationContext) {
    this.authenticationContext = authenticationContext;
  }

  @ServiceRef(attributeId = AuthnrQdslUtilConstants.PROP_AUTHORIZATION_QDSL_UTIL, defaultValue = "",
      attributePriority = P3_AUTHORIZATION_QDSL_UTIL, label = "Authorization QDSL Util target",
      description = "OSGi service filter to identify the AuthorizationQdslUtil service.")
  public void setAuthorizationQdslUtil(final AuthorizationQdslUtil authorizationQdslUtil) {
    this.authorizationQdslUtil = authorizationQdslUtil;
  }

}
