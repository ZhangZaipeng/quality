/**
 * @(#)CertificateVerifier.java Copyright 2011 jointown, Inc. All rights reserved.
 */
package com.hhh.quality.platform.login.impl;

import com.hhh.quality.platform.login.Verifier;
import java.security.cert.X509Certificate;

/**
 * 证书认证校验器
 *
 * @author jianguo.xu
 * @version 1.0, 2011-3-10
 */
public class CertificateVerifier implements Verifier {

  private X509Certificate cert;

  public CertificateVerifier(X509Certificate cert) {
    this.cert = cert;
  }

  public X509Certificate getCert() {
    return cert;
  }

  public void setCert(X509Certificate cert) {
    this.cert = cert;
  }
}
