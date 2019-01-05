package com.ace.alfox.lib.data;

import java.util.Collection;
import org.dizitart.no2.*;
import org.dizitart.no2.event.ChangeListener;
import org.dizitart.no2.meta.Attributes;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;

class ObjectRepositoryFacade<T> implements ObjectRepository<T> {

  private final ObjectRepository<T> objectRepository;

  ObjectRepositoryFacade(ObjectRepository<T> objectRepository) {
    this.objectRepository = objectRepository;
  }

  @Override
  public WriteResult insert(T object, T... others) {
    return objectRepository.insert(object, others);
  }

  @Override
  public WriteResult update(ObjectFilter filter, T update) {
    return objectRepository.update(filter, update);
  }

  @Override
  public WriteResult update(ObjectFilter filter, T update, boolean upsert) {
    return objectRepository.update(filter, update, upsert);
  }

  @Override
  public WriteResult update(ObjectFilter filter, Document update) {
    return this.objectRepository.update(filter, update);
  }

  @Override
  public WriteResult update(ObjectFilter filter, Document update, boolean justOnce) {
    return this.objectRepository.update(filter, update, justOnce);
  }

  @Override
  public WriteResult remove(ObjectFilter filter) {
    return this.objectRepository.remove(filter);
  }

  @Override
  public WriteResult remove(ObjectFilter filter, RemoveOptions removeOptions) {
    return this.objectRepository.remove(filter, removeOptions);
  }

  @Override
  public Cursor<T> find() {
    return this.objectRepository.find();
  }

  @Override
  public Cursor<T> find(ObjectFilter filter) {
    return this.objectRepository.find(filter);
  }

  @Override
  public Cursor<T> find(FindOptions findOptions) {
    return this.objectRepository.find(findOptions);
  }

  @Override
  public Cursor<T> find(ObjectFilter filter, FindOptions findOptions) {
    return this.objectRepository.find(filter, findOptions);
  }

  @Override
  public Class<T> getType() {
    return this.objectRepository.getType();
  }

  @Override
  public NitriteCollection getDocumentCollection() {
    return this.objectRepository.getDocumentCollection();
  }

  @Override
  public void createIndex(String field, IndexOptions indexOptions) {
    this.objectRepository.createIndex(field, indexOptions);
  }

  @Override
  public void rebuildIndex(String field, boolean async) {
    this.objectRepository.rebuildIndex(field, async);
  }

  @Override
  public Collection<Index> listIndices() {
    return this.objectRepository.listIndices();
  }

  @Override
  public boolean hasIndex(String field) {
    return this.objectRepository.hasIndex(field);
  }

  @Override
  public boolean isIndexing(String field) {
    return this.objectRepository.isIndexing(field);
  }

  @Override
  public void dropIndex(String field) {
    this.objectRepository.dropIndex(field);
  }

  @Override
  public void dropAllIndices() {
    this.objectRepository.dropAllIndices();
  }

  @Override
  public WriteResult insert(T[] elements) {
    return this.objectRepository.insert(elements);
  }

  @Override
  public WriteResult update(T element) {
    return this.objectRepository.update(element);
  }

  @Override
  public WriteResult update(T element, boolean upsert) {
    return this.objectRepository.update(element);
  }

  @Override
  public WriteResult remove(T element) {
    return this.objectRepository.remove(element);
  }

  @Override
  public T getById(NitriteId nitriteId) {
    return this.objectRepository.getById(nitriteId);
  }

  @Override
  public void drop() {
    this.objectRepository.drop();
  }

  @Override
  public boolean isDropped() {
    return this.objectRepository.isDropped();
  }

  @Override
  public boolean isClosed() {
    return this.objectRepository.isClosed();
  }

  @Override
  public void close() {
    this.objectRepository.close();
  }

  @Override
  public String getName() {
    return this.objectRepository.getName();
  }

  @Override
  public long size() {
    return this.objectRepository.size();
  }

  @Override
  public void register(ChangeListener listener) {
    this.objectRepository.register(listener);
  }

  @Override
  public void deregister(ChangeListener listener) {
    this.objectRepository.deregister(listener);
  }

  @Override
  public Attributes getAttributes() {
    return this.objectRepository.getAttributes();
  }

  @Override
  public void setAttributes(Attributes attributes) {
    this.objectRepository.setAttributes(attributes);
  }
}
