package io.fursan.inventorymanagement.mapper;

public interface Mapper<S, T> {
  T mapTo(S source);

  S mapFrom(T target);
}
