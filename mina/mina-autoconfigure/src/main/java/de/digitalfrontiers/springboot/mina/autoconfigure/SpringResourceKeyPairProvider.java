package de.digitalfrontiers.springboot.mina.autoconfigure;

import org.apache.sshd.common.keyprovider.AbstractResourceKeyPairProvider;
import org.apache.sshd.common.session.SessionContext;
import org.apache.sshd.common.util.io.resource.IoResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.Collection;

/**
 * A {@link org.apache.sshd.common.keyprovider.KeyPairProvider} adapter, that delegates to Spring {@link Resource}. This
 * adapter allows the MINA to use the Spring Frameworks {@link Resource resource abstraction} to be used as a {@link
 * org.apache.sshd.common.keyprovider.KeyPairProvider key pair provider}. This is achieved by using the {@link
 * SpringIoResource Spring specific} {@link IoResource} implementation.
 *
 * @see SpringIoResource
 */
public class SpringResourceKeyPairProvider extends AbstractResourceKeyPairProvider<Resource> {

  private final Collection<Resource> resources;

  public SpringResourceKeyPairProvider(Resource... resources) {
    this(Arrays.asList(resources));
  }

  public SpringResourceKeyPairProvider(Collection<Resource> resources) {
    if (resources == null)
      throw new IllegalArgumentException("no resources provided");

    this.resources = resources;
  }

  public Collection<Resource> getResources() {
    return resources;
  }

  @Override
  public Iterable<KeyPair> loadKeys(SessionContext session) throws IOException, GeneralSecurityException {
    return loadKeys(session, getResources());
  }

  @Override
  protected IoResource<?> getIoResource(SessionContext session, Resource resource) {
    return new SpringIoResource(resource);
  }
}
