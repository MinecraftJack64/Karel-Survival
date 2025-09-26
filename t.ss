a^b
#a, b -> a^b
dupe top
dupe top
#abbb
rot four
rot four
rot four
#bbba
dupe top
#bbbaa
rot four
rot four
<<
#ba(a)b(b)
rot four
rot four
#bb(b)a(a)
rot four
dupe top
#b(a)b(b)aa...
#babbaa
mult()
#babb(a*a)
rot four
#...
#ba(a)b(b)
<< if(b)
#ba(a**a)b0
pop top
rot four
rot four
pop top
pop top
pop top
#a^a

subtract(1):
#a->a-1
#Get -1
dupe top
dupe top
#aaa
invert
binary add
#a(-1)
binary add
#a-1

mult():
#a, b

FINAL:
