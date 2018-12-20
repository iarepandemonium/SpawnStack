package net.pandette.spawn_stack.di;

import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import net.pandette.spawn_stack.StackLocation;
import net.pandette.spawn_stack.StackerConfiguration;
import net.pandette.spawn_stack.listeners.CreatureListener;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerSingleComponent implements SingleComponent {
  private Provider<StackerConfiguration> providesStackerConfigurationProvider;

  private Provider<StackLocation> providesStackLocationProvider;

  private Provider<CreatureListener> providesCreatureListenerProvider;

  private DaggerSingleComponent(Builder builder) {
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {

    this.providesStackerConfigurationProvider =
        DoubleCheck.provider(
            SingleModule_ProvidesStackerConfigurationFactory.create(builder.singleModule));

    this.providesStackLocationProvider =
        DoubleCheck.provider(
            SingleModule_ProvidesStackLocationFactory.create(
                builder.singleModule, providesStackerConfigurationProvider));

    this.providesCreatureListenerProvider =
        DoubleCheck.provider(
            SingleModule_ProvidesCreatureListenerFactory.create(
                builder.singleModule, providesStackLocationProvider));
  }

  @Override
  public CreatureListener listener() {
    return providesCreatureListenerProvider.get();
  }

  public static final class Builder {
    private SingleModule singleModule;

    private Builder() {}

    public SingleComponent build() {
      if (singleModule == null) {
        throw new IllegalStateException(SingleModule.class.getCanonicalName() + " must be set");
      }
      return new DaggerSingleComponent(this);
    }

    public Builder singleModule(SingleModule singleModule) {
      this.singleModule = Preconditions.checkNotNull(singleModule);
      return this;
    }
  }
}
