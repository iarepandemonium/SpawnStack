package net.pandette.spawn_stack.yaml;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class StackYaml_Factory implements Factory<StackYaml> {
  private final Provider<CustomYamlFile> customProvider;

  public StackYaml_Factory(Provider<CustomYamlFile> customProvider) {
    assert customProvider != null;
    this.customProvider = customProvider;
  }

  @Override
  public StackYaml get() {
    return new StackYaml(customProvider.get());
  }

  public static Factory<StackYaml> create(Provider<CustomYamlFile> customProvider) {
    return new StackYaml_Factory(customProvider);
  }
}
