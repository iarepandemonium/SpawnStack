package net.pandette.spawn_stack.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import net.pandette.spawn_stack.StackLocation;
import net.pandette.spawn_stack.listeners.CreatureListener;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SingleModule_ProvidesCreatureListenerFactory
    implements Factory<CreatureListener> {
  private final SingleModule module;

  private final Provider<StackLocation> stackLocationProvider;

  public SingleModule_ProvidesCreatureListenerFactory(
      SingleModule module, Provider<StackLocation> stackLocationProvider) {
    assert module != null;
    this.module = module;
    assert stackLocationProvider != null;
    this.stackLocationProvider = stackLocationProvider;
  }

  @Override
  public CreatureListener get() {
    return Preconditions.checkNotNull(
        module.providesCreatureListener(stackLocationProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<CreatureListener> create(
      SingleModule module, Provider<StackLocation> stackLocationProvider) {
    return new SingleModule_ProvidesCreatureListenerFactory(module, stackLocationProvider);
  }

  /** Proxies {@link SingleModule#providesCreatureListener(StackLocation)}. */
  public static CreatureListener proxyProvidesCreatureListener(
      SingleModule instance, StackLocation stackLocation) {
    return instance.providesCreatureListener(stackLocation);
  }
}
