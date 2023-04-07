package manuall.altCrud.dao

interface DAO<T> {
    fun create(entity:T): T?
    fun read(): List<T>
    fun update(entity:T, id:Int) :T?
    fun delete(id:Int) :Int?
    fun findId(id:Int):T?
}