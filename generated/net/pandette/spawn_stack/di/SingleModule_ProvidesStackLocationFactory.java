package net.pandette.spawn_stack.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import net.pandette.spawn_stack.StackLocation;
import net.pandette.spawn_stack.StackerConfiguration;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SingleModule_ProvidesStackLocationFactory implements Factory<StackLocation> {
  private final SingleModule module;

  private final Provider<StackerConfiguration> stackerProvider;

  public SingleModule_ProvidesStackLocationFactory(
      SingleModule module, Provider<StackerConfiguration> stackerProvider) {
    assert module != null;
    this.module = module;
    assert stackerProvider != null;
    this.stackerProvider = stackerProvider;
  }

  @Override
  public StackLocation get() {
    return Preconditions.checkNotNull(
        module.providesStackLocation(stackerProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<StackLocation> create(
      SingleModule module, Provider<StackerConfiguration> stackerProvider) {
    return new SingleModule_ProvidesStackLocationFactory(module, stackerProvider);
  }

  /** Proxies {@link SingleModule#providesStackLocation(StackerConfiguration)}. */
  public static StackLocation proxyProvidesStackLocation(
      SingleModule instance, StackerConfiguration stacker) {
    return instance.providesStackLocation(stacker);
  }
}
