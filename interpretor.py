def POP_TOP(stack):
    print("POP_TOP "+str(stack[-1]))
    """Remove and discard the top of the stack."""
    stack.pop()

def DUP_TOP(stack):
    print("DUP_TOP "+str(stack[-1]))
    """Duplicate the top of the stack."""
    stack.append(stack[-1])

def ROT_FOUR(stack):
    """Rotate the top four stack items one step left:
       [a, b, c, d] → [d, a, b, c]"""
    a, b, c, d = stack[-4:]
    print("ROT_FOUR ", str(a), str(b), str(c), str(d))
    stack[-4:] = [d, a, b, c]

def UNARY_INVERT(stack):
    print("UNARY_INVERT "+str(stack[-1]))
    """Bitwise invert the top of the stack (~x)."""
    stack[-1] = ~stack[-1]

def BINARY_ADD(stack):
    print("BINARY_ADD "+str(stack[-1])+str(stack[-2]))
    """Pop the top two stack items, add, push result."""
    b = stack.pop()
    a = stack.pop()
    stack.append(a + b)

def POP_JUMP_IF_TRUE(stack, target, pc):
    print("POP_JUMP_IF_TRUE")
    """Pop top of stack; if truthy, jump to target offset.
       Returns the new program counter (pc)."""
    value = stack.pop()
    if value:
        return target/2-1
    return pc
def mult(stack):
    b = stack.pop()
    a = stack.pop()
    stack.append(a * b)
c = 20
b = 4
a = 3
stack = [a, b]
instructions = []
id = 0
def convert_instructions():
    while True:
        l = input()
        if "END" in l:
            break
        if "DUP_TOP" in l:
            instructions.append(1)
        elif "POP_TOP" in l:
            instructions.append(0)
        elif "ROT_FOUR" in l:
            instructions.append(2)
        elif "UNARY_INVERT" in l:
            instructions.append(3)
        elif "BINARY_ADD" in l:
            instructions.append(4)
        elif "POP_JUMP_IF_TRUE" in l:
            p = l.split(" ")
            instructions.append(5)
            instructions.append(p[1])
        elif "mult()" in l:
            instructions.append(-1)
convert_instructions()
print(instructions)
while id<len(instructions):
    ic = instructions[id]
    if ic==0:
        POP_TOP(stack)
    elif ic==1:
        DUP_TOP(stack)
    elif ic==2:
        ROT_FOUR(stack)
    elif ic==3:
        UNARY_INVERT(stack)
    elif ic==4:
        BINARY_ADD(stack)
    elif ic==5:
        id+=1
        POP_JUMP_IF_TRUE(stack, int(instructions[id]), id)
    elif ic==-1:
        mult(stack)
        print("mult()")
    print("STACK: "+str(stack))
    id+=1
print(stack)