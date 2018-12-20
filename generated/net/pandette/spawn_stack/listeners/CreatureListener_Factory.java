package net.pandette.spawn_stack.listeners;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import net.pandette.spawn_stack.StackLocation;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CreatureListener_Factory implements Factory<CreatureListener> {
  private final Provider<StackLocation> stackLocationProvider;

  public CreatureListener_Factory(Provider<StackLocation> stackLocationProvider) {
    assert stackLocationProvider != null;
    this.stackLocationProvider = stackLocationProvider;
  }

  @Override
  public CreatureListener get() {
    return new CreatureListener(stackLocationProvider.get());
  }

  public static Factory<CreatureListener> create(Provider<StackLocation> stackLocationProvider) {
    return new CreatureListener_Factory(stackLocationProvider);
  }
}
