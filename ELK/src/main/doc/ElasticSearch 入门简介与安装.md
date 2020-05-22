## ElasticSearch 入门简介与安装

### 一、安装

1. 下载：https://www.elastic.co/cn/downloads/elasticsearch

选择合适的版本进行下载

2. 解压安装：进入文件目录

3. 启动：bin/elasticsearch

4. 验证：访问 localhost:9200,如果出现以下内容则表示安装成功：

   ```json
   {
     "name" : "Xp6GcoT",
     "cluster_name" : "elasticsearch",
     "cluster_uuid" : "Sbk-IMRCSgKTUEuW8ygOTQ",
     "version" : {
       "number" : "6.8.7",
       "build_flavor" : "oss",
       "build_type" : "tar",
       "build_hash" : "c63e621",
       "build_date" : "2020-02-26T14:38:01.193138Z",
       "build_snapshot" : false,
       "lucene_version" : "7.7.2",
       "minimum_wire_compatibility_version" : "5.6.0",
       "minimum_index_compatibility_version" : "5.0.0"
     },
     "tagline" : "You Know, for Search"
   }
   ```

### 二、基本概念

#### 全文搜索(Full-text Search)

  全文检索是指计算机索引程序通过扫描文章中的每一个词，对每一个词建立一个索引，指明该词在文章中出现的次数和位置，当用户查询时，检索程序就根据事先建立的索引进行查找，并将查找的结果反馈给用户的检索方式。
  在全文搜索的世界中，存在着几个庞大的帝国，也就是主流工具，主要有：

- Apache Lucene
- Elasticsearch
- Solr
- Ferret

#### 倒排索引（Inverted Index）

  该索引表中的每一项都包括一个属性值和具有该属性值的各记录的地址。由于不是由记录来确定属性值，而是由属性值来确定记录的位置，因而称为倒排索引(inverted index)。Elasticsearch能够实现快速、高效的搜索功能，正是基于倒排索引原理。

#### 节点 & 集群（Node & Cluster）

  Elasticsearch 本质上是一个分布式数据库，允许多台服务器协同工作，每台服务器可以运行多个Elasticsearch实例。单个Elasticsearch实例称为一个节点（Node），一组节点构成一个集群（Cluster）。只要将不同的实例节点改成相同的节点名称即可组成一个集群。

#### 索引（Index）

  Elasticsearch 数据管理的顶层单位就叫做 Index（索引），相当于关系型数据库里的数据库的概念。另外，每个Index的名字必须是小写。

#### 文档（Document）

  Index里面单条的记录称为 Document（文档）。许多条 Document 构成了一个 Index。Document 使用 JSON 格式表示。同一个 Index 里面的 Document，不要求有相同的结构（scheme），但是最好保持相同，这样有利于提高搜索效率。

#### 类型（Type）

  Document 可以分组，比如employee这个 Index 里面，可以按部门分组，也可以按职级分组。这种分组就叫做 Type，它是虚拟的逻辑分组，用来过滤 Document，类似关系型数据库中的数据表。
  不同的 Type 应该有相似的结构（Schema），性质完全不同的数据（比如 products 和 logs）应该存成两个 Index，而不是一个 Index 里面的两个 Type（虽然可以做到）。

### 文档元数据（Document metadata）

  文档元数据为_index, _type, _id, 这三者可以唯一表示一个文档，_index表示文档在哪存放，_type表示文档的对象类别，_id为文档的唯一标识。

#### 字段（Fields）

  每个Document都类似一个JSON结构，它包含了许多字段，每个字段都有其对应的值，多个字段组成了一个 Document，可以类比关系型数据库数据表中的字段。
  在 Elasticsearch 中，文档（Document）归属于一种类型（Type），而这些类型存在于索引（Index）中，下图展示了Elasticsearch与传统关系型数据库的类比：

| 关系型数据库 | ElasticSearch |
| :----------: | :-----------: |
|   DataBase   |     Index     |
|    Table     |     Type      |
|     Rows     |   Documents   |
|   Columns    |    Fields     |





