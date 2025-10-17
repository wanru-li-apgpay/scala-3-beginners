package com.rockthejvm.part2oop

object K_Enums {

  // ============================================
  // 🎯 Scala 3 的枚举（enum）
  // - 枚举用于表示固定的有限集合值，例如权限、状态、颜色等
  // - Scala 3 引入了更強大的 enum 特性，比 Java enum 更靈活
  // ============================================

  // 定义一个简单枚举 Permissions
  enum Permissions {
    case READ, WRITE, EXECUTE, NONE // 四个枚举值

    // 枚举类中可以定义方法
    def openDocument(): Unit =
      if (this == READ) println("opening document...") // 只有 READ 可以打开文档
      else println("reading not allowed.")            // 其他权限不允许
  }

  // 创建一个枚举实例
  val somePermissions: Permissions = Permissions.READ


  // ==========================
  // 枚举的标准 API
  // ==========================
  val somePermissionsOrdinal = somePermissions.ordinal
  // ordinal: 枚举值在定义中的索引，READ=0, WRITE=1, ...

  val allPermissions = PermissionsWithBits.values
  // values: 返回枚举中所有可能的值，类型是 Array[PermissionsWithBits]

  val readPermission: Permissions = Permissions.valueOf("READ")
  // valueOf: 根据字符串返回对应枚举实例

  // ==========================
  // 枚举带构造参数
  // ==========================
  enum PermissionsWithBits(val bits: Int) { // 每个权限对应一个二进位标记
    case READ extends PermissionsWithBits(4)    // 二进制 100
    case WRITE extends PermissionsWithBits(2)   // 二进制 010
    case EXECUTE extends PermissionsWithBits(1) // 二进制 001
    case NONE extends PermissionsWithBits(0)    // 二进制 000
    case ALL extends PermissionsWithBits(7)   // 二进制 111 (READ + WRITE + EXECUTE)
  }

  // 可以给枚举对象添加辅助方法
  object PermissionsWithBits {
    // 根据 bits 值返回对应枚举（示例，这里简单返回 NONE）
    def fromBits(bits: Int): PermissionsWithBits =
      PermissionsWithBits.NONE
  }

  // ==========================
  // 测试
  // ==========================
  def main(args: Array[String]): Unit = {
    somePermissions.openDocument() // 输出 "opening document..."

    println(somePermissionsOrdinal) // 输出 0
    println(allPermissions.mkString(", ")) // 输出所有权限
    println(readPermission)

    val read = PermissionsWithBits.READ
    val write = PermissionsWithBits.WRITE
    println(s"READ bits: ${read.bits}") // 4
    println(s"WRITE bits: ${write.bits}") // 2


    val permissionMethod = PermissionsWithBits.fromBits(4)
    println(permissionMethod)
  }
}
