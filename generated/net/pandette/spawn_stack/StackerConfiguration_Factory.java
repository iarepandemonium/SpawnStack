package net.pandette.spawn_stack;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.bukkit.configuration.file.FileConfiguration;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class StackerConfiguration_Factory implements Factory<StackerConfiguration> {
  private final Provider<FileConfiguration> configurationProvider;

  public StackerConfiguration_Factory(Provider<FileConfiguration> configurationProvider) {
    assert configurationProvider != null;
    this.configurationProvider = configurationProvider;
  }

  @Override
  public StackerConfiguration get() {
    return new StackerConfiguration(configurationProvider.get());
  }

  public static Factory<StackerConfiguration> create(
      Provider<FileConfiguration> configurationProvider) {
    return new StackerConfiguration_Factory(configurationProvider);
  }
}
