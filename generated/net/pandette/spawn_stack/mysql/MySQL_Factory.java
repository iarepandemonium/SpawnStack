package net.pandette.spawn_stack.mysql;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import net.pandette.spawn_stack.StackerConfiguration;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MySQL_Factory implements Factory<MySQL> {
  private final Provider<StackerConfiguration> configurationProvider;

  public MySQL_Factory(Provider<StackerConfiguration> configurationProvider) {
    assert configurationProvider != null;
    this.configurationProvider = configurationProvider;
  }

  @Override
  public MySQL get() {
    return new MySQL(configurationProvider.get());
  }

  public static Factory<MySQL> create(Provider<StackerConfiguration> configurationProvider) {
    return new MySQL_Factory(configurationProvider);
  }
}
