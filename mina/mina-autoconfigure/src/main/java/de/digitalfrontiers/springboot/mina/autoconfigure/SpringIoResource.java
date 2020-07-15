package de.digitalfrontiers.springboot.mina.autoconfigure;

import org.apache.sshd.common.util.io.resource.AbstractIoResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Spring {@link org.apache.sshd.common.util.io.resource.IoResource} adapter, delegating to a Spring {@link Resource}.
 */
public class SpringIoResource extends AbstractIoResource<Resource> {
  public SpringIoResource(Resource resource) {
    super(Resource.class, resource);
  }

  @Override
  public InputStream openInputStream() throws IOException {
    return getResourceValue().getInputStream();
  }
}
