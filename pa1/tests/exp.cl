(* test number *)
TEST_NUMBER_____________
23.45
0033
1232434335456213133245346549023823498397120930786408237678612873691287631231234234
1e10
0000002
0.0004

(* TEST ALPHABET *)
TEST_ALPHA_________
q
w
e
r
t
y
u
i
o
p
a
s
d
f
g
h
j
k
l
z
x
c
v
b
n
m
Q
W
E
R
T
Y
U
I
O
P
A
S
D
F
G
H
J
K
L
Z
X
C
V
B
N
M


(* test white space *)
TEST_WHITE_SPACE_____________
	

 
\b
\v
\t
\n
\f
\\
\c
\d
\e
\"
\
\b\t\n\f\\\c\d\e\"\

(* test true false value  *)
TEST_TRUE_FALSE____________
true
True
tRUe
fALse
faLSe
fAlse
False

(* test identifier *)
TEST_INDENTIFIER____________
helloworld
helloWorld
hello_world
hello-world
HelloWorld
hELLOWORLD
_helloworld
-helloworld
1helloworld
helloworld1
1Helloworld
Helloworld1
helloworld!
123h



(* test string constructions *)
TEST_STRING_CONSTRUCTION_________________
'hello world'
"hello world"
"\h\e\l\l\o \w\or\l\d"
"hello \b world"
"hello \t world"
"hello \f world"
"hello \r world"
"hello \v world"
"hello world""hello
"hello world"""
""hello world"
"""hello world"
"hello \n world"
"hello \0 world"
"hello \t world"
"hello	world"
"hello \'yes' world"
"hello \"yes" world"
"hello \\n world"
"hello
world"
"hello \
world"
"hello \ 
world"
"hello \a
world"
"hello (* world *)"
"hello \"world\""

(* test comment *)
TEST_COMMENT___________
-- test comment --
-- test comment
test comment --
-- -- -- --
-- (* 
-- *)
(* -- *)
(* test (* comment *) *)
(* test (* comment *)
(* test comment *) *)
(* This is (* a valid comment *)? *)
(*(*(*(**)*)*)*)
(*(*(*(*ax*)ax*)ax*)ax*)
(*ax(*ax(*ax(**)*)*)*)
unterminated *)

TEST_LONG_STRINGS___________
(* test long string 1024 *)
"xy6IzAeELTIc5CxeJW5UmeJMKZrWNOaXrZ8oHbArV8YfLgZUmrNlU4ZdKfAL89R9yzzS6iQ0SoC9zoHEzKOYcMUsRXigp08DCEdt0T1sf6Ub8haUHpU4n4plA53bxuMkX4IczRBcyu6gzha5sOvgEa2oGCKc9Ttcw0ZHkvFg9AHahZryrEox9iluEWSX5iQ5r1kCQefdUX2Lp3oSfFS3zuQp5MMbx0gA8ibxxri3VNBeLNL9EikMOP16lfGM2miA3n3newlexPoXnT7qoArXGkJJd7oBMeFJz39O7BXE6jkLogUL2I2BiLGoEC1ZwLszpUl1N8NPn2G54AFu9YxgRIuB4YLqow5ZcpMFGWyCf5ocYuENt3tNSEKaQk9JLKBPDOxMqL78s2PlNRpcW30wd9x8EaMt0W2qCiPKQJHtrQbFboGQUP0w85jwwodDicLHvhmvN1D9fDZjwrKx5pQPm8UHAr6SACYJIdkBNJflWfqaZX00u2WJ8n9umVQK0JW6JLFJm9NCJ1I97IgaNS1mMsSmTEWpPacMmqqtSXdquwQfQUJXCpbBVgutSGDTZKtkTHsnyQlLxUoZAjcntPfkWz2c84FnbVNJa19dTMo13mJfY8UlJA19ezMgIkgfrgqheY4MIUAPNF7UeAqcdg6AgBqN76vwjFVyhpggABub2WJE1GZIPxYuJKuHHLg7fOXY5sPcLB98WDEEFm0sGSIatBRdC5truzIlx8SXjwt03KSykMdvc6Ysf7x6xnB4OtSZkA3CD3PWfvXGpYYJmzPZ15D7iADg4pXb6lOLj0HvqWAvXCQ8mBadylM922U7xOiZEqf0uN9WrkAolMoqdC8OYcrvjN0o6DgpDDg3eBsR3lhOvbCtjaT918Y8PW5WWUCDDKiCbYW6RpJyaMA0qxyAomsePy4dhrNIEZRI4HOLg5WfeTjtHDUC7SD1Zjtnr1o9T7pMCqFRIgh1bxCCB6XQXL16usBX3ayZbt3WpPp7fHU34wr1mMwOMVj9zAumJ1Bx"
(* test long string 1025 *)
"GEe2PfMnHK8eCz5AYE0ewWuwHjt4NC3AKh7h6CoC7bajaMohxBGk4ZKH4uZOHis3vHMrXV5vHvH3DYL7jDFSqaTS60pUcYYPYnm0v0Co3JYtevspggTB7cxmWpH3m8haEEVd1rMH3reZreYkIiOCDFAHhkBtIXqfyYA6fOHLPHBpfLCvTyDtHI0bTxhpEdH7PzbzQHKMEZ3peVEBXTDHheJ0IfGJuKiW1LTdkcyTUwSvv2OBBMrFV4RRURYVMO2CZYkhEk9a1Bs9t52m1eWgIA0PeEw2tcjbcaAB83zSDAaJkGqkDvJnt8qcItmWc4ISu7CJ7VB44OM7FTvNkej0j7JchnLevR7yfjkdezCYTDXR29rd3OZUSZh3FcO1XrIo0C9EoYqyQnQl23hCKTuaZFn0j3pcRZbgAVGTj8Yf6Gno1IdKJQPHjxr8vd44JaHbwZMILFV68qhmwjwA9hBN4GBgptljOzygoAUWxz62uwLYvBSXBlbf3SrSdk6HtDNdZuRCRtE86l8woKSD2YZoDZPMe13oAwn6RbYqD4zM8cmD1Zp8RQpWdYHPrdTSuG1yqqAkAWVMy7smxf1x3P8qHQ9KgQpnBZWDtBEise0QClzB30qwSIICcsX9fkwOwi9xj879L6BEoi8ezu3bLw3mQsW6FZTU41OJLWrUEbllbwyHm7RPwzWkL96ajV1SagaYIqzK5Ws0qBQ8J9hBDAVpQHoJpoQxcnCQtwjTICxR1qlikwu8vn58iTctLVvt1tkDFRNG0xqFYsPSKadjAhddpQDtVBITkKvDSRCeoZi3XJ5xlHZTlj5idgFDGFd9n0gbZcrAtGalKm5OiPHdJDMInJWc5kgTSap1RWVW052xmQ8rk4RWW2KmJXSiHwRoJ3IOrLOiNlWwbrdWHPjwUkn8j6hqTuIunynudlBRZe3o5I3JoAD2VupAQ7MmbvOqNbRIvW0mFc9ctRJlC7HieSmQrTggqzXPxSJA2WLPFyndf1s6ABwO8qu4lDFZ1sQTjXmrP"
(* test long string 1026 *)
"We26lJxSp9bGoNcZVsmH7Q5pHUIxNNPKgqzFhhlmzLrp7JUyF0VBNYxbR9SgM8daXeSFkDlblyZ6lgwobEsah3UWKNUzvmT9xOx25IqXVAK0wHDx3pDc45lwa6rIcpjfR6BnUn9fHqHI8co6O0YTh9PoPSEidKV6y5uVvnsqW4ISyuTJM5HvllCidjYHqV73SbKSBhgnHVquF0uz5yd8kmWqvMnPSypW0IrPQKKEgRinJ1LCMVJBnu1Ib88jApIW7IaHoXBRIcjqqOh3nMt739OwHcNGqr7T7myG8BgpzmEKUWOEih75um5AtxKwiqCFUhcDLDvooKk5bu6rg98H4bdRSyj9KfQSRUczN7NA6nrxVPvuS1fg4eBILhBFBUiOTQ2XrHSUR5mD8brdQMp7ayOLqVtiYrJjCFTTiAvp0HmRwhMDZF9iG3PSfL4yDwwLQ4XPKd4vmk2TO0rXxQemAW3GgTAbtuLWeGjX8T5r0y22yo9sOljJxycg2VusEmdJURQfHmdJ1lASxec5lpmaVAdE6om8o0p0SdibxGdtUHft1uiBWMG9fIKzG5IAuPuYxUWqzGOEBDiTVspUmPcjpoXt2tisYFjRxjEYLzngE15empE6AEwHlVLrzoWbFE3dcT5RqsXKdxGu2EeZrdK7UqjK2Jbp0oB6izL60Bv7ZWWwByEC3Fm1fCsxF6gSBl0yo4Aih1mx20zFyrLLTkvcJl88SezPbqg3YPJs20BCPQmwUZzxicTRv86b6vmkDORL3emkZkVMYFPSLItTwqfiD6ck9I8SGo4mFiK1Pe8UHYJtEMXZg1ZhnR9nsEr4aHhBzE7fzAYY93zGzzhV1LuCYtW83IQiK3GDM6oLGaj7888v2zjp8gXURgn5XlWoSWv832clJkR37vJmHvgiViAWxPFi0yqiYYXXj0zkhtHBEKBFtyu6HdhcVlk7n9FvWCK59d9zBQ6XnnoaanMNg2fMquwpZzsTU9bcRTuw3enekoC8r9AvX63WDTHj21WSK7IIpJ"


(* remaining cases *)
REMAINING_CASES________
self
SELF_TYPE

(* keywords *)
KEYWORDS________
class
else
false
fi
if
in
inherits
isvoid
let
loop
pool
then
while
case
esac
new
of
not
true
,
.
:
"
'
[
]
{
}
+
=
-
(
)
*
&
^
%
$
#
@
!
`
~
\
|
/
>
<
.
?
_
+
\,
\.
\:
\"
\'
\[
\]
\{
\}
\+
\=
\-
\(
\)
\*
\&
\^
\%
\$
\#
\@
\!
\`
\~
\\
\|
\/
\>
\<
\.
\?
\_
\+
