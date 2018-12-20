package net.pandette.spawn_stack.mysql;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class StackSql_Factory implements Factory<StackSql> {
  private final Provider<MySQL> mySQLProvider;

  public StackSql_Factory(Provider<MySQL> mySQLProvider) {
    assert mySQLProvider != null;
    this.mySQLProvider = mySQLProvider;
  }

  @Override
  public StackSql get() {
    return new StackSql(mySQLProvider.get());
  }

  public static Factory<StackSql> create(Provider<MySQL> mySQLProvider) {
    return new StackSql_Factory(mySQLProvider);
  }
}
