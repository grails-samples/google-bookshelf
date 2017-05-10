package bookshelf.model

import groovy.transform.CompileStatic

@CompileStatic
class Result<K> {
    String cursor
    List<K> result

    Result(List<K> result, String cursor) {
        this.result = result
        this.cursor = cursor
    }

    Result(List<K> result) {
        this.result = result
        this.cursor = null
    }
}
