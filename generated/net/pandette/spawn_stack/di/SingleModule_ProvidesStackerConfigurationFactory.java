package net.pandette.spawn_stack.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import net.pandette.spawn_stack.StackerConfiguration;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SingleModule_ProvidesStackerConfigurationFactory
    implements Factory<StackerConfiguration> {
  private final SingleModule module;

  public SingleModule_ProvidesStackerConfigurationFactory(SingleModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public StackerConfiguration get() {
    return Preconditions.checkNotNull(
        module.providesStackerConfiguration(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<StackerConfiguration> create(SingleModule module) {
    return new SingleModule_ProvidesStackerConfigurationFactory(module);
  }

  /** Proxies {@link SingleModule#providesStackerConfiguration()}. */
  public static StackerConfiguration proxyProvidesStackerConfiguration(SingleModule instance) {
    return instance.providesStackerConfiguration();
  }
}
